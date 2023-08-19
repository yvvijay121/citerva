import { Handler, HandlerEvent } from '@netlify/functions';
import fetch from 'node-fetch';

export const handler: Handler = async (event: HandlerEvent) => {
    if (!event.queryStringParameters?.doi) {
        return {
            statusCode: 400,
            body: JSON.stringify({
                message: `Bad request. Expected a DOI query parameter.`,
            }),
        };
    } else {
        const [openAlexResponse, unpaywallResponse] = await Promise.all([
            fetch(`https://api.openalex.org/works/doi:${event.queryStringParameters.doi}`),
            fetch('url2'),
        ]);

        const openAlexData = await openAlexResponse.json() as Record<string, any>;

        var conceptListString = 'https://api.openalex.org/concepts?select=display_name,ids,description,image_url,level,wikidata&filter=openalex:';
        for (let i = 0; i < openAlexData.concepts.length; i++) conceptListString += openAlexData.concepts[i].id.split("/")[3] + '|';
        conceptListString = conceptListString.slice(0, -1);

        const [journalResponse, doiLinkResponse, conceptsResponse] = await Promise.all([
            fetch(`https://api.openalex.org/sources/S${openAlexData.primary_location.source.id.split('S')[1]}`),
            fetch("https://doi.org/api/handles/" + event.queryStringParameters.doi + "?type=URL"),
            fetch(conceptListString),
        ]);
        // create an array of abstract words
        let abstract: string[] = [];

        // reverse the abstract inverted index
        for (let key in openAlexData.abstract_inverted_index) {
            for (let num in openAlexData.abstract_inverted_index[key]) {
                abstract[openAlexData.abstract_inverted_index[key][num]] = key;
            }
        }

        // if abstract is not empty, join the words together
        if ((abstract.join(' ')).length > 0 && abstract.join(' ')) {
            openAlexData.abstract = abstract.join(' ');
        } else {
            openAlexData.abstract = 'No abstract available.';
        }

        // add information to article data
        openAlexData.open_access = await unpaywallResponse.json() as Record<string, any>;
        openAlexData.journal_information = await journalResponse.json() as Record<string, any>;
        openAlexData.doi_link = await doiLinkResponse.json() as Record<string, any>;

        // TODO: add concepts to article data (with wikipedia information)

        // return the article data
        return {
            statusCode: 200,
            body: JSON.stringify(openAlexData),
        };
    }
};
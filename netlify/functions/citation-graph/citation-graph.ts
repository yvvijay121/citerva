import { Handler, HandlerEvent } from '@netlify/functions';
import fetch from 'node-fetch';

export const handler: Handler = async (event: HandlerEvent) => {
  if (!event.queryStringParameters?.openalexID) {
    return {
      statusCode: 400,
      body: JSON.stringify({
        message: `Bad request. Expected a DOI query parameter.`,
      }),
    };
  } else {
    const openalexID: string = event.queryStringParameters.openalexID;
    const requestString = `https://api.openalex.org/works/${openalexID}`;
    // get the citation graph from the OpenAlex API
    const response = await fetch(requestString);
    const data: Record<string, unknown> = await response.json() as Record<string, unknown>;

    let nodes: Object = {root: {name: openalexID}};
    let edges: Object = {};

    for(let work of data.referenced_works as String[]) {
      let id = work.split('/')[3];
      nodes[id] = {name: id};
      edges[id] = {source: "root", target: id};
    }

    // return the citation graph
    return {
      statusCode: 200,
      body: JSON.stringify({
        nodes: nodes,
        edges: edges,
      }),
    };
  }
};
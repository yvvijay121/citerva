import { Handler, HandlerEvent } from '@netlify/functions'
import fetch from 'node-fetch'

export const handler: Handler = async (event: HandlerEvent) => {
  if(!event.queryStringParameters?.doi) {
    return {
      statusCode: 400,
      body: JSON.stringify({
        message: `Bad request. Expected a DOI query parameter.`,
      }),
    }
  } else {
    const doi = event.queryStringParameters.doi
    fetch(`https://api.openalex.org/works/doi:${doi}`)
      .then(response => response.json())
      .then(data => {
        console.log(data);
      })
    return {
      statusCode: 200,
      body: JSON.stringify({
        message: `Hello from citation-graph.ts! You requested the citation graph for DOI ${doi}.`,
      }),
    }
  }
}
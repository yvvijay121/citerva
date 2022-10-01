# Citerva

Hello! Citerva, which isn't finished yet, is an open-access, open-source bibliometrics application to search up and analyze research articles and its related information, such as authors, cited articles, related concepts, and more! This project is based off of the [OpenAlex project](https://openalex.org/), an open and comprehensive catalog of research papers.

![image](https://user-images.githubusercontent.com/62124499/193377370-b3330b22-9b10-4040-9c5c-eb2f44a949ba.png)

## Structure

When completed, the project will consist of two parts:
```mermaid
graph LR
    A[(OpenAlex)] --> |OpenAlex API| D
    B[(Unpaywall)] --> |Unpaywall API| D
    C[DOI Content Negotiation] --> D
    subgraph Docker Compose
        D(Javalin Backend) <--> E(Vue frontend)
    end
```

At this point, the Javalin backend isn't really necessary; it only does some small data-processing and retrieving open access information from Unpaywall, another open-access data source. It *will* create graph objects for certain visualizations. However, I do see this becoming useful later on, when I would want to incorporate other data sources that may provide information on articles that haven't been logged in the OpenAlex database (DOI content negotiation).

## Goals & Plans
- [x] Get the project functional
  - [x] Get the Javalin backend working (completed, may add more functionality)
  - [x] Get the Vue frontend working (only template project for now)
  - [x] Create the Docker Compose file for it to work
- [x] Create basic UI for Vue frontend
  - [x] Add basic article information
  - [ ] Implement network graph
  - [ ] Add related sources (get information from Wikipedia)
- [x] Make everything look fancy!
- [ ] Future Ideas:
  - [ ] Add author information from ORCID (when provided)
      - Can use [ORCID content negotiation](https://github.com/ORCID/ORCID-Source/blob/master/CONTENT_NEGOTIATION.md)
  - [ ] Add further publishing information?
  - [ ] Add more data source (DOI content negotiation, Semantic Scholar)
    - Note: There's not really any need to add data sources such as PubMed and ARXIV, since OpenAlex does catalog these sources. It *would* be nice, however.
  - [ ] Could add a general search for concepts, text, etc.

## Useful Links for later
- https://github.com/ORCID/ORCID-Source/blob/master/CONTENT_NEGOTIATION.md
- https://github.com/citation-js/citation-js
- https://github.com/citation-js/replacer#starting.example
- https://github.com/Juris-M/citeproc-js
- https://www.semanticscholar.org/product/api
- https://api.semanticscholar.org/api-docs/graph#tag/Paper-Data
- https://github.com/allenai/s2orc-doc2json

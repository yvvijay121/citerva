<script setup lang="ts">
import { useRoute } from 'vue-router';
import { ref } from 'vue';
import CitationBox from '../components/CitationBox.vue';
import JournalPublisherBox from '../components/JournalPublisherBox.vue';
import ConceptBox from '../components/ConceptBox.vue';
import ArticleButtons from '../components/ArticleButtons.vue';
import Graph from '@/components/Graph.vue';

let route = useRoute();
let loading = ref(true);
const articleObject: any = ref({});
const currentTab = ref('abstract');

const doi = ref((<string[]>route.params.doi).join('/'))
if (route.params.doi) {
  fetch(`https://api.openalex.org/works/doi:${doi.value}`)
    .then(res => res.json())
    .then(json => {
      articleObject.value = json;

      articleObject.value.abstract = 'No abstract available.';
      
      let abstract: string[] = [];

      for(let key in articleObject.value.abstract_inverted_index) {
          for(let num in articleObject.value.abstract_inverted_index[key]){
              abstract[articleObject.value.abstract_inverted_index[key][num]] = key;
          }
      }

      articleObject.value.abstract = abstract.join(' ');

      loading.value = false;
    });
}

// create a function called "tabSwitch" that takes in a tab name and sets the current tab to that tab
const tabSwitch = (tab: string) => {
  currentTab.value = tab
}
</script>
<style lang="scss" scoped>
.modal-background-lighter {
  background-color: rgba(0, 0, 0, 0.25);
}

.capitalize {
  text-transform: capitalize;
}

.is-tall {
  height: calc(100vh - 12.5em)
}

.v-enter-active,
.v-leave-active {
  transition: opacity 0.1875s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
</style>
<template>
  <transition mode="out-in">
    <div class="
          hero
          columns
          is-mobile is-vcentered is-centered is-tall
        " v-if="loading">
      <div class="hero-body column is-narrow">
        <div class="box has-text-centered">
          <img class="brandimage" :src="require('../assets/image.svg')" />
          <progress class="progress is-primary" max="100">15%</progress>
        </div>
      </div>
    </div>
    <div class="container is-fluid" v-else>
      <div class="columns is-mobile is-centered mx-4">
        <div class="column is-three-quarters-desktop is-two-thirds-tablet">
          <div class="box">
            <h1 class="title is-2 mb-1">{{ articleObject.title }}</h1>
            <nav class="breadcrumb has-dot-separator is-size-7 mb-2">
              <ul>
                <li><a :href="articleObject.doi">{{ articleObject.doi }}</a></li>
                <li><a :href="articleObject.doi">{{ doi }}</a></li>
              </ul>
            </nav>
            <div class="box is-rounded has-background-light p-3">
              <span class="is-6 title mx-1">Authors:</span>
              <span class="
                    has-background-dark has-text-white
                    is-size-7
                    p-2
                    mx-1
                    mt-1
                    is-rounded
                    tag
                  " v-for="(item, index) in articleObject.authorships" :key="index">{{ item.author.display_name
                  }}</span>
            </div>
          </div>
          <div class="tabs">
            <ul>
              <li :class="{ 'is-active': currentTab === 'abstract' }"><a @click="tabSwitch('abstract')">Abstract &
                  Concept
                  Information</a></li>
              <li :class="{ 'is-active': currentTab === 'related' }"><a @click="tabSwitch('related')">Related
                  Information</a></li>
              <li :class="{ 'is-active': currentTab === 'citation' }"><a @click="tabSwitch('citation')">Citations</a>
              </li>
            </ul>
          </div>
          <div v-if="currentTab === 'abstract'">
            <ConceptBox :concepts="articleObject.concepts" />
            <div class="box">
              <h3 class="title is-3 mb-2">Abstract</h3>
              <p>{{ articleObject.abstract }}</p>
            </div>
          </div>
          <div v-if="currentTab === 'related'">
            <Graph :doi="doi" />
          </div>
          <div v-if="currentTab === 'citation'">
            <div class="box">
              <h3 class="title is-3 mb-2">Citations</h3>

            </div>
          </div>
        </div>
        <div class="column is-one-quarters-desktop is-one-thirds-tablet">
          <CitationBox :doi="doi" />
          <ArticleButtons :doi="doi" />
          <JournalPublisherBox :source="articleObject.primary_location.source.id.split('S')[1]" />
        </div>
      </div>
    </div>
  </transition>
</template>
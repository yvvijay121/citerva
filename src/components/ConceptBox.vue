<script setup lang="ts">
import { ref } from 'vue';
import ConceptObj from '../components/ConceptObj.vue';
const props = defineProps({
  concepts: { type: Object, required: true }
});

const detailedConcepts: any = ref({});

var conceptListString = 'https://api.openalex.org/concepts?select=display_name,ids,description,image_url,level,wikidata&filter=openalex:';
for (let i = 0; i < props.concepts.length; i++) conceptListString += props.concepts[i].id.split("/")[3] + '|';
conceptListString = conceptListString.slice(0, -1);

fetch(conceptListString)
  .then(res => res.json())
  .then(json => {
    detailedConcepts.value = json;
    // reverse the order of the concepts so that the most specific concepts are first
    detailedConcepts.value.results.reverse();
  });
</script>

<style lang="scss" scoped>
@import "node_modules/nord/src/sass/nord.scss";

.concept-obj {
  width: fit-content;
  margin: 0.5rem;
}

.concept-obj-container {
  display: grid;
  grid-auto-flow: column;
  overflow-x: scroll;
  scrollbar-width: thin;
  scrollbar-color: $nord9 $nord6;
  overflow-y: hidden;
}
</style>

<template>
  <h3 class="title is-3 mb-2">Concepts</h3>
  <div class="concept-obj-container">
    <ConceptObj v-for="concept in detailedConcepts.results" :concept="concept" />
  </div>
</template>
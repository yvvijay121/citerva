<script setup lang="ts">
import { ref, onBeforeMount } from 'vue'
const props = defineProps({
  concepts: { type: Object, required: true }
})

var conceptListString = 'https://api.openalex.org/concepts?filter=openalex:';
for (let i = 0; i < props.concepts.length; i++) conceptListString += props.concepts[i].id.split("/")[3] + '|';
conceptListString = conceptListString.slice(0, -1);

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
}
</style>

<template>
  <div class="box">
    <h3 class="title is-3 mb-2">Abstract</h3>
    <div class="concept-obj-container">
      <div class="box concept-obj" v-for="concept in concepts">
        <h6 class="title is-6 mb-1">{{ concept.display_name }}</h6>
        <p>{{ concept.id }}</p>
      </div>
    </div>
  </div>
</template>
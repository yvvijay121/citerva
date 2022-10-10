<script setup lang="ts">
import { ref } from 'vue';
const props = defineProps({
  doi: { type: String, required: true }
})
const citation = ref('Loading citation...');
const citationtype = ref("mla");
const loading = ref(true);
const options = [
  { text: 'MLA', value: 'modern-language-association' },
  { text: 'APA', value: 'apa' },
  { text: 'Chicago', value: 'chicago-fullnote-bibliography' }
];
function cite() {
  loading.value = true;
  fetch('https://doi.org/' + props.doi, {
    headers: {
      Accept: 'text/x-bibliography; style=' + citationtype.value,
    },
  }).then((res) => res.text())
    .then((text) => (citation.value = text))
    .then(() => (loading.value = false));
}
cite();
</script>

<template>
  <article class="message box is-secondary p-0">
    <div class="message-header level m-0">
      <div class="level-left m-0">
        <p>Cite this article</p>
      </div>
      <div class="level-right m-0">
        <div class="select is-fullwidth is-small is-secondary">
          <select v-model="citationtype" @change="cite()">
            <option v-for="option in options" :value="option.value">
              {{ option.text }}
            </option>
          </select>
        </div>
      </div>
    </div>
    <div class="message-body">
      <div class="box citation">
        <Transition mode="out-in">
          <p class="citation" v-if="loading">Give us a moment to load this citation. It may take a bit.</p>
          <p class="citation" v-else>{{ citation }}</p>
        </Transition>
      </div>
    </div>
  </article>
</template>

<style lang="scss" scoped>
@import "node_modules/nord/src/sass/nord.scss";

.bulma-overlay-mixin {
  background-color: darkorange;
  border-radius: 0.25em;
  color: white;
  opacity: 0.9;
  padding: 1em;
}

.citation {
  background-color: #2e3440;
  border-radius: 0.25em;
  color: white;
  opacity: 0.9;
  padding: 1em;
}

.citation>p {
  font-family: monospace;
  font-size: 0.75em;
}
</style>

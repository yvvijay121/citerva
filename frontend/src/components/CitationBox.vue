<script setup lang="ts">
import { ref } from 'vue';
const props = defineProps({
  doi: { type: String, required: true }
})
const citation = ref('Error loading citation.');
const citationtype = ref("mla");
const options = [
  { text: 'MLA', value: 'mla' },
  { text: 'APA', value: 'apa' },
  { text: 'Toronto', value: 'toronto' }
];
function cite() {
  fetch('https://doi.org/' + props.doi, {
    headers: {
      Accept: 'text/x-bibliography; style=' + citationtype.value,
    },
  }).then((res) => res.text())
    .then((text) => (citation.value = text));
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
      <p>{{ citation }}</p>
    </div>
  </article>
</template>

<style lang="scss" scoped>

</style>

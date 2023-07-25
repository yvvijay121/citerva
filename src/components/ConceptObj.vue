<script setup lang="ts">
import { ref } from 'vue'
const props = defineProps({
  concept: { type: Object, required: true }
})

// asynchroneously fetch the description of the concept from wikipedia if it is not already present
const description = ref(props.concept.description)
const image_url = ref(props.concept.image_url)

fetch(`https://en.wikipedia.org/api/rest_v1/page/summary/${props.concept.ids.wikipedia.split("/")[4]}`)
  .then(res => res.json())
  .then(json => {
    description.value = json.extract ?? props.concept.description
    image_url.value = json.thumbnail?.source ?? json.originalimage?.source ?? props.concept.image_url
  }).catch(() => {
    description.value = props.concept.description
    image_url.value = props.concept.image_url
  })
</script>
<style lang="scss" scoped>
@import "node_modules/nord/src/sass/nord.scss";

.box-concept {
  width: 15vw;
  max-height: 30vh;
  margin: 0.5rem;
  background-repeat: no-repeat;
  background-size: cover;
  opacity: 0.8;
  scrollbar-width: thin;
  scrollbar-color: $nord9 rgba(0, 0, 0, 0);
  overflow-y: auto;
}
</style>
<template>
  <div class="box box-concept"
    :style="{ backgroundImage: `linear-gradient(rgba(0,0,0,0.95), rgba(0,0,0,0.65)), url(${image_url})` }">
    <h6 class="title is-6 mb-1 has-text-white">
      <a :href="`${props.concept.ids.wikipedia}`">
        {{ props.concept.display_name }}
      </a>
    </h6>
    <p class="is-7 has-text-white">{{ description }}</p>
  </div>
</template>
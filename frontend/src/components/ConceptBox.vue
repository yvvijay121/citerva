<script setup lang="ts">
import { ref, onBeforeMount } from 'vue'
const props = defineProps({
  concepts: { type: Object, required: true }
})
const conceptObjects = ref([]); // array of concept objects
onBeforeMount(() => {
  var conceptMassRequestURL = "https://api.openalex.org/concepts?filter=openalex:"
  var concept = props.concepts;
  for(var i = 0; i < concept.length; i++) {
    console.log(concept[i]);
  }
})
</script>
<style lang="scss" scoped>
.concept-image {
  background-repeat: no-repeat;
  background-position: center;
}
</style>
<template>
  <!-- TODO: Finish component by figuring out how to make one mass request to prevent 429 errors, and how to make it look nice. -->
    <!-- <div class="background box is-2 column concept-image" :style="{ backgroundImage: `url(${concept.image_url})` }">
      <h3 class="title is-3">{{ concept.name }}</h3>
      <p>{{ concept.description }}</p>
    </div> -->
</template>
<script setup lang="ts">
import { ref } from 'vue';
const props = defineProps({
  primary_location: { type: Object, required: true }
});
const host: any = ref({});
let not_available: boolean = false;

if (!!props.primary_location.source) {
  fetch(`https://api.openalex.org/sources/S${props.primary_location.source.id.split('S')[1]}`)
    .then(res => res.json())
    .then(json => {
      host.value = json;
    });
} else {
  not_available = true;
}
</script>
<style lang="scss" scoped>
.is-7 {
  font-size: 0.85em;
}

.mxt-0-5 {
  margin-left: 0.125em;
  margin-right: 0.125em;
}
</style>
<template>
  <div class="box" v-if="not_available">
    <h6 class="is-6 title">Journal & Publishing Information</h6>
    <h4>Sorry! The publishing information isn't available for this paper.</h4>
  </div>
  <div class="box" v-else>
    <h6 class="is-6 title">Journal & Publishing Information</h6>
    <p class="is-7">
      <span>Journal: </span>
      <span class="has-text-weight-semibold">{{ host.display_name ?? 'Not available.' }}</span>
    </p>
    <p class="is-7">
      <span>Publisher: </span>
      <span class="has-text-weight-semibold">{{ host.host_organization_name ?? 'Not available.' }}</span>
    </p>
    <p class="is-7">
      <span>ISSN: </span>
      <span class="has-text-weight-semibold">{{ host.issn_l ?? 'Not available.' }}</span>
    </p>
    <p class="is-7">
      <span>Homepage: </span>
      <span class="has-text-weight-semibold"><a :href="host.homepage_url">{{ host.homepage_url ?? 'Not available.'
      }}</a></span>
    </p>
    <div>
      <span class="is-7">Concepts: </span>
      <span class="
              has-background-link has-text-white
              is-size-7
              is-rounded
              tag
              mxt-0-5
            " v-for="concept in (host.x_concepts ?? ['Not available.']).slice(0, 5)" :key="concept">
        <span>{{ concept.display_name }}</span>
      </span>
    </div>
  </div>
</template>
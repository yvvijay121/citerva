<script setup lang="ts">
import { ref, onBeforeMount } from 'vue'
const props = defineProps({
  host: { type: Object, required: true }
})
</script>
<style lang="scss" scoped>
  .is-7{
    font-size: 0.85em;
  }
  .mxt-0-5{
    margin-top: 0.25em;
    margin-left: 0.125em;
    margin-right: 0.125em;
  }
</style>
<template>
    <div class="box">
        <h6 class="is-6 title">Journal & Publishing Information</h6>
        <p class="is-7">
            <span>Journal: </span>
            <span class="has-text-weight-semibold">{{ props.host.display_name }}</span>
        </p>
        <p class="is-7">
            <span>Publisher: </span>
            <span class="has-text-weight-semibold">{{ props.host.host_organization }}</span>
        </p>
        <p class="is-7">
            <span>ISSN: </span>
            <span class="has-text-weight-semibold">{{ props.host.issn_l }}</span>
        </p>
        <p class="is-7">
            <span>Homepage: </span>
            <span class="has-text-weight-semibold"><a :href="props.host.homepage_url">{{ props.host.homepage_url }}</a></span>
        </p>
        <div>
          <span class="is-7">Concepts: </span>
          <span class="
              has-background-link has-text-white
              is-size-7
              is-rounded
              tag
              mxt-0-5
            " v-for="concept in props.host.x_concepts.slice(0,5)" :key="concept">
            <span>{{ concept.display_name }}</span>
          </span>
        </div>
    </div>
</template>
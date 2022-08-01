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
            <span class="has-text-weight-semibold">{{ props.host.publisher }}</span>
        </p>
        <p class="is-7">
            <span>ISSN: </span>
            <span class="has-text-weight-semibold">{{ props.host.issn_l }}</span>
        </p>
    </div>
</template>
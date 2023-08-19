<script setup lang="ts">
import { ref, Ref } from 'vue';
// get URL arguments
const urlParams = new URLSearchParams(window.location.search);
const searchQuery: Ref<Record<string, unknown>> = ref({});
const query = urlParams.get('query');
fetch(`https://api.openalex.org/works?search=${query}`)
    .then((response) => response.json())
    .then((data) => {
        searchQuery.value = data.results;
    });
</script>
<style lang="scss" scoped></style>
<template>
    <div class="container is-fluid">
        <div class="columns is-mobile is-centered mx-4">
            
        </div>
    </div>
</template>
<script setup lang="ts">
import { ref } from 'vue';
const props = defineProps({
    doi: { type: String, required: true }
});

let link = ref('');

fetch("https://doi.org/api/handles/" + props.doi + "?type=URL")
    .then(res => res.json())
    .then(json => {
        link.value = json.values[0].data.value;
    });
</script>
<template>
    <nav class="breadcrumb has-dot-separator is-size-7 mb-2">
        <ul>
            <li><a :href="'https://doi.org/' + doi">doi:{{ doi }}</a></li>
            <!-- TODO: fix this to use DOI content negotiation(?) and get the actual link -->
            <li><a :href="link">{{ link }}</a></li>
        </ul>
    </nav>
</template>
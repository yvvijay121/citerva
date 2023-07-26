<script setup lang="ts">
import { ref } from 'vue';
import { VNetworkGraph } from 'v-network-graph';
import 'v-network-graph/lib/style.css';
const props = defineProps({
    openalexID: { type: String, required: true }
});

const graphObject = ref({});

// netlify function
fetch(`/.netlify/functions/citation-graph?openalexID=${props.openalexID}`)
    .then(res => res.json())
    .then(json => {
        graphObject.value = json;
    });
</script>
<style lang="scss" scoped>
.graph {
    width: fit-parent;
    height: 50vh;
    border: 1px solid #000;
}
</style>
<template>
    <div class="box">
        <h3 class="title is-3 mb-2">Citation Graph</h3>
        <v-network-graph class="graph" :nodes="graphObject.nodes" :edges="graphObject.edges" />
    </div>
</template>
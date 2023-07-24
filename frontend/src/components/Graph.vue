<script setup lang="ts">
import { ref } from 'vue'
import { VNetworkGraph } from 'v-network-graph'
import 'v-network-graph/lib/style.css'
const props = defineProps({
    doi: { type: String, required: true }
})

const graphObject = ref({});

fetch(`http://localhost/api/graph/${props.doi}`)
    .then(res => res.json())
    .then(json => graphObject.value = json);


const nodes = {
    node1: { name: "Node 1" },
    node2: { name: "Node 2" },
    node3: { name: "Node 3" },
    node4: { name: "Node 4" },
}

const edges = {
    edge1: { source: "node1", target: "node2" },
    edge2: { source: "node2", target: "node3" },
    edge3: { source: "node3", target: "node4" },
}
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
        {{ graphObject }}
        <v-network-graph class="graph" :nodes="nodes" :edges="edges" />
    </div>
</template>
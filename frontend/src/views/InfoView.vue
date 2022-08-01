<script setup lang="ts">
import { useRoute } from 'vue-router'
import { onBeforeMount, ref, reactive, watch } from 'vue'
import CitationBox from '../components/CitationBox.vue'

let route = useRoute()
const articleObject: any = ref({})
const link = ref('')
const currentTab = ref('abstract')

onBeforeMount(() => {
  if (route.params.doi) {
    let doi = <string[]>route.params.doi
    // fetch('http://localhost/api/doi/' + doi.join('/'))
    fetch('https://api.openalex.org/works/doi:' + doi.join('/'))
      .then((res) => res.json())
      .then((json) => (articleObject.value = json));
    fetch('https://doi.org/api/handles/' + doi.join('/') + '?type=URL')
      .then((res) => res.json())
      .then((json) => (link.value = json.values[0].data.value));
  }
})

// create a function called "tabSwitch" that takes in a tab name and sets the current tab to that tab
const tabSwitch = (tab: string) => {
  currentTab.value = tab
}
</script>
<style lang="scss" scoped>
  .modal-background-lighter {
    background-color: rgba(0, 0, 0, 0.25);
  }
  .capitalize {
    text-transform: capitalize;
  }
</style>
<template>
  <div class="container is-fluid">
    <div class="columns">
      <div class="column is-three-quarters-desktop is-two-thirds-tablet">
        <div class="box">
          <div class="columns is-v-centered is-desktop">
            <div class="column">
              <h1 class="title is-2">{{ articleObject.title }}</h1>
            </div>
            <div class="column is-narrow has-text-right">
              <a href="#" class="button is-danger m-1">
                <span class="icon is-small">
                  <i class="fas fa-file-pdf"></i>
                </span>
                <span>Article PDF</span>
              </a>
              <br class="is-hidden-touch">
              <a href="#" class="button is-primary m-1">
                <span class="icon is-small">
                  <i class="fas fa-file-alt"></i>
                </span>
                <span>Full Text</span>
              </a>
            </div>
          </div>
          <div class="box is-rounded has-background-light p-3">
            <span class="is-6 title mx-1">Authors:</span>
            <span class="
                has-background-dark has-text-white
                is-size-7
                p-2
                mx-1
                mt-1
                is-rounded
                tag
              " v-for="(item, index) in articleObject.authorships" :key="index">{{ item.author.display_name }}</span>
          </div>
        </div>
        <div class="tabs">
          <ul>
            <li :class="{'is-active': currentTab === 'abstract'}"><a @click="tabSwitch('abstract')">Abstract & Concept Information</a></li>
            <li :class="{'is-active': currentTab === 'related'}"><a @click="tabSwitch('related')">Related Information</a></li>
            <li :class="{'is-active': currentTab === 'citation'}"><a @click="tabSwitch('citation')">Citations</a></li>
          </ul>
        </div>
        <div class="box">
          <h3 class="title is-3">Abstract</h3>
          <p>{{link}}</p>
          <div>
            <p>{{ articleObject.abstract }}</p>
          </div>
        </div>
        
      </div>
      <div class="column">
        <CitationBox doi="10.1126/science.169.3946.635" default="mla" />
        <article class="box p-0 message is-success">
          <div class="message-header">
            <p class="capitalize">Open Access Status:</p>
            <!-- <p class="capitalize">Open Access Status: {{ articleObject.unpaywall.oa_status }}</p> -->
          </div>
          <div class="message-body">
            <div class="content">
              <a href="#" class="button is-danger m-1">
                <span class="icon is-small">
                  <i class="fas fa-file-pdf"></i>
                </span>
                <span>Article PDF</span>
              </a>
              <a href="#" class="button is-primary m-1">
                <span class="icon is-small">
                  <i class="fas fa-file-alt"></i>
                </span>
                <span>Full Text</span>
              </a>
              <a href="#" class="button is-link m-1">
                <span class="icon is-small">
                  <i class="fas fa-info-circle"></i>
                </span>
                <span>What is this?</span>
              </a>
            </div>
          </div>
        </article>
        <div class="box">
          <h6 class="is-6 title">Journal & Publishing Information</h6>
          <p class="is-6">
            <span>Journal:</span>
            <span class="has-text-weight-semibold">a</span>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>
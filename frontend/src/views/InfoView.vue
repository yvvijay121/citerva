<script setup lang="ts">
import { useRoute } from 'vue-router'
import { onBeforeMount, ref, reactive, watch } from 'vue'

let route = useRoute()
const articleObject: any = ref({})

onBeforeMount(() => {
  if (route.params.doi) {
    let doi = <string[]> route.params.doi
    fetch('http://localhost/api/doi/' + doi.join('/'))
    .then((res) => res.json())
    .then((json) => (articleObject.value = json));
  }
})
</script>
<style lang="scss" scoped>
.modal-background-lighter{
  background-color: rgba(0, 0, 0, 0.25);
}

.capitalize {
  text-transform: capitalize;
}
</style>
<template>
  <!-- <div class="modal is-active">
    <div class="modal-background modal-background-lighter"></div>
    <div class="modal-content">
    
    </div>
  </div> -->
  <div class="container">
    <div class="columns">
      <div class="column is-8">
        <h1 class="title is-2">{{ articleObject.title }}</h1>
        <div class="box is-rounded has-background-light p-3">
          <span class="is-6 title mx-1">Authors:</span>
          <span
            class="
              has-background-dark has-text-white
              is-size-7
              p-2
              mx-1
              is-rounded
              tag
            "
            v-for="(item, index) in articleObject.authorships"
            :key="index"
            >{{ item.author.display_name }}</span
          >
        </div>
        <div class="box">
          <h3 class="title is-3">Abstract</h3>
          <div>
            <p>{{ articleObject.abstract }}</p>
          </div>
        </div>
      </div>
      <div class="column is-4">
        <article class="box p-0 message is-success">
          <div class="message-header">
            <p class="capitalize">Open Access Status: {{ articleObject.unpaywall.oa_status }}</p>
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
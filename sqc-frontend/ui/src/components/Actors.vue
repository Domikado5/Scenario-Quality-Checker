<template>
    <div class="row mx-1">
        <div class="mb-3 row align-items-center gx-2 gy-3">
            <div class="col-lg-10 col-12">
                <label for="nameInput" class="form-label">Actor Name</label>
                <input type="text" id="nameInput" v-model="name" class="form-control">
            </div>
            <div class="col-lg-1 col-6 row justify-content-center align-items-center p-0">
                <label for="systemInput" class="form-label col-12">System Actor</label>
                <input type="checkbox" id="systemInput" v-model="system" class="form-check-input p-0">
            </div>
            <div class="col-lg-1 col-6 d-flex justify-content-center align-items-center">
                <button @click="addActor" class="btn btn-outline-success"><i class="bi bi-plus-circle-fill"></i></button>
            </div>
        </div>
        <div class="mb-3 px-3">
            <div v-for="actor in actors" :key="actor" class="row mb-2 border border-info p-1 mx-0 align-items-center">
                <div class="col-6">{{ actor.name }}</div>
                <div class="col-3">{{ actor.system }}</div>
                <div class="col-3">
                    <button @click="removeActor(actor.name)" class="btn btn-outline-danger"><i class="bi bi-trash-fill"></i></button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: "Actors",
  components: {},
  props: {
      value: Array
  },
  data(){
      return {
          actors: this.value,
          name: "",
          system: false,
      }
  },
  methods: {
      addActor(){
          if (this.name.length > 0){
            const actor = {
                name: this.name,
                system: this.system
            }
            this.actors.push(actor)
            this.name = ""
            this.system = false
            this.$emit("inputActor", this.actors)
        }   
      },
      removeActor(name){
          this.actors = this.actors.filter((actor) => {
              return actor.name != name
          })
          this.$emit("inputActor", this.actors)
      }
  }
};
</script>

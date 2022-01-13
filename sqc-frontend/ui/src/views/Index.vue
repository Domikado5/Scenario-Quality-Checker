<template>
  <div class="container-fluid">
    <h1>Scenario Quality Checker</h1>
    <div class="accordion" id="accordionExample">
    <div class="accordion-item">
        <h2 class="accordion-header" id="headingOne">
        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
            Scenario Form
        </button>
        </h2>
        <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
        <div class="accordion-body">
            <form @submit.prevent="" class="row">
        <div class="mb-3">
            <label for="titleInput" class="form-label">Scenario title</label>
            <input type="text" class="form-control" id="titleInput" aria-describedby="titleHelp" v-model="scenario.title">
            <div id="titleHelp" class="form-text">Type your scenario title</div>
        </div>
        <hr>
        <div class="mb-3">
            <label for="actorsInput" class="form-label">Scenario Actors</label>
            <actors id="actorsInput" :value="scenario.actors" @inputActor="scenario.actors = $event" aria-describedby="actorsHelp"></actors>
            <div id="actorsHelp" class="form-text">Type your scenario actors</div>
        </div>
        <hr>
        <div class="mb-3">
            <label for="inputLevel" class="form-label">Max Level of steps</label>
            <input type="number" id="inputLevel" class="form-control" aria-describedby="levelHelp" v-model="scenario.maxLevel">
            <div id="levelHelp" class="form-text">This will affect the displayed steps</div>
        </div>
        <hr>
        <div class="mb-3">
            <label for="stepsInput" class="form-label">Steps</label>
            <steps id="stepsInput" :value="scenario.steps" :root="true" @inputStep="scenario.steps = $event" :depth-value="0" aria-describedby="stepsHelp"></steps>
            <div id="stepsHelper" class="form-text">Type your steps</div>
        </div>
        <hr>
        <div class="mb-3">
            <button class="btn btn-outline-warning" @click="submitForm">Submit</button>
        </div>
    </form>
        </div>
        </div>
    </div>
    <div class="accordion-item">
        <h2 class="accordion-header" id="headingTwo">
        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
            Scenario Result
        </button>
        </h2>
        <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
        <div class="accordion-body">
            <scenario-result :value="result" :key="reload"></scenario-result>
        </div>
        </div>
    </div>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import Actors from "@/components/Actors.vue";
import Steps from '@/components/Steps.vue';
import ScenarioResult from '../components/ScenarioResult.vue';

export default {
  name: "Index",
  components: { Actors, Steps, ScenarioResult },
  data(){
      return {
          scenario: {
              title: "",
              actors: [],
              maxLevel: 0,
              steps: [],
              reload: 0
          },
          result: {
    "title": "Dodanie ksiazki",
    "actors": [
        "Bibliotekarz"
    ],
    "system_actors": [
        "System"
    ],
    "steps": [
        "0~1.Bibliotekarz cos robi",
        "0~2.IF Bibliotekarz pragnie sera",
        "1~3.Dentysta wybiera ser"
    ],
    "keyword_count": 1,
    "steps_count": 3,
    "invalid_steps": [
        "3. Dentysta wybiera ser"
    ],
    "errors": []
}
      }
  },
  methods: {
        async submitForm(){
            console.log(this.scenario)
            const scenario = {
                title: this.scenario.title,
                actors: this.scenario.actors.filter((actor) => actor.system == false),
                system_actors: this.scenario.actors.filter((actor) => actor.system == true),
                maxLevel: this.scenario.maxLevel,
                steps: this.scenario.steps
            }
            this.result = await this.fetchUtil('127.0.0.1:8090/scenario', 'POST', scenario, null)
            document.querySelector("h2#headingTwo button").click()
            console.log(this.result)
            this.reload++
        }
  }
};
</script>

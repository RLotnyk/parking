const car={template:`
<div>

<button type="button"
class="btn btn-primary m-2 fload-end"
data-bs-toggle="modal"
data-bs-target="#exampleModal"
@click="addClick()">
 Add Car
</button>

<table class="table table-striped">
<thead>
    <tr>
        <th>Owner</th>
        <th>Car Number</th>
        <th>Options</th>
    </tr>
</thead>
<tbody>
    <tr v-for="car in cars">
        <td>{{car.car_owner}}</td>
        <td>{{car.car_number}}</td>
        <td>
            <button type="button" @click="deleteClick(car.id)"
            class="btn btn-light mr-1">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"/>
                </svg>
            </button>
        </td>
    </tr>
</tbody>
</thead>
</table>

<div class="modal fade" id="exampleModal" tabindex="-1"
    aria-labelledby="exampleModalLabel" aria-hidden="true">
<div class="modal-dialog modal-lg modal-dialog-centered">
<div class="modal-content">
    <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">{{modalTitle}}</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"
        aria-label="Close"></button>
    </div>

    <div class="modal-body">
    <div class="d-flex flex-row bd-highlight mb-3">
        <div class="p-2 w-50 bd-highlight">
            <div class="input-group mb-3">
                <span class="input-group-text">Owner</span>
                <input type="text" class="form-control" v-model="owner">
            </div>
        </div>

         <div class="p-2 w-50 bd-highlight">
              <div class="input-group mb-3">
                     <span class="input-group-text">Number</span>
                     <input type="text" class="form-control" v-model="number">
              </div>
         </div>

    </div>
        <button type="button" v-on:click="createClick()" class="btn btn-primary">
            Save
        </button>
    </div>
</div>
</div>
</div>


</div>

`,

data() {
    return {
        cars:[],
        modalTitle:"",
        id:0,
        owner:"",
        number:"",
        car_owner:"",
        car_number:"",
    }
},

methods:{
    refreshData(){
        axios.get(variables.API_URL + "car")
        .then((response)=>{
            this.cars=response.data;
        });
    },
    addClick(){
        this.modalTitle="Add Car";

    },

    createClick() {
        axios.post(variables.API_URL + "car/?owner=" + this.owner + '&number=' + this.number
          ).then((response)=>{
                this.refreshData();
                console.log(response)
        });
    },
    deleteClick(id) {
        if(!confirm("Confirm Delete ?")){
            return;
        }
        axios.delete(variables.API_URL + "car/" + id)
        .then((response)=>{
            this.refreshData();
            console.log(response);
        });
    },
},
mounted:function(){
    this.refreshData();
}
}

let  token = localStorage.getItem("Authorization")
new Vue({
    el:"#app",
    data:{
        paymentPage:"",
    },
    mounted() {
        let itemString = localStorage.getItem('addItemList');
        items = itemString.substr(0, itemString.length - 2).split('， ');
        courseId = [];
        items.forEach(function(item){
            let i = item.lastIndexOf("d")
            let id = item.substring(i+1)
            courseId.push(id)
        });
        axios.post(`/checkout`,courseId , {
            headers: {
                Authorization: token
            }
        }).then((res) =>{
            items.forEach(function(item){
                localStorage.removeItem(item);
            });
            localStorage.removeItem("addItemList")
            this.paymentPage = res.data.paymentPage;
            $('#app').html(res.data.paymentPage);
        }).catch((err)=>{
            window.location.replace("/");
            window.alert("請登入會員")
        })
    },
})







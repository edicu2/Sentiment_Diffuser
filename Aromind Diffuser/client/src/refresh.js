import EventBus from '../src/components/EventBus'

export default function (Vue) {

  Vue.refresh = {
    checktoken () {
      axios
        .get("api/profile/",{
          headers: { Authorization: `Bearer ${localStorage.usertoken}` }
        })
        .then((res) => {
            EventBus.$emit('logged-in', 'loggedin')
          }).catch((err) => {
            console.log(err)
            console.log(err.response)
          })
      },
  }
  Object.defineProperties(Vue.prototype, {
    $refresh: {
      get () {
        return Vue.refresh
      }
    }
  })
    
}
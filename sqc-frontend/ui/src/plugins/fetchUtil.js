export default {
    install: (app) => {
        app.config.globalProperties.fetchUtil = (url, method, body, token) => {
          console.log(url)
          const fetchBody = {
            method: method, 
            mode: 'cors', 
            cache: 'no-cache', 
            credentials: 'same-origin', 
            headers: {
              'Accept': 'application/json',
              'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
            },
            redirect: 'follow', 
            referrerPolicy: 'no-referrer', 
            ...(method!="GET") && {body: JSON.stringify(body)}
          }
          const data = fetch("http://127.0.0.1:8090/scenario", fetchBody)
            .then(response => {
              console.log('Got response with satus code ' + response.status + ' : ' + response.statusText)
              console.log(response)
              if (response.status!==200 && response.status!==204){
                return response.json()
              }else if (response.status == 204){
                return {'code': response.status, 'text': response.statusText}
              }
              return response.json()
            })
            .catch(err => {
                console.error(err)
                
            })
          return data
          }
          app.provide('fetchUtil')
    }
}
/**
 * Module      : Action
 * Description : Shorthand methods for creating user interaction components
 * Dependencies: Vue, Ionic 
 * Author      : Junhyun Kim <junbread@skku.edu>
 */
export default {
    install(Vue, options) {
        Vue.prototype.$action = {
            confirm(message) {
                return new Promise((onConfirm, onCancel) => {
                    Vue.prototype.$ionic.alertController
                        .create({
                            header: 'Confirm',
                            message: message,
                            buttons: [
                                {
                                    text: 'Yes',
                                    handler: onConfirm
                                },
                                {
                                    text: 'No',
                                    handler: onCancel
                                }
                            ]
                        })
                        .then(a => a.present())
                })
            },
            toast(message, duration) {
                return new Promise(() => {
                    Vue.prototype.$ionic.toastController
                        .create({
                            message: message,
                            duration: duration ? duration : 2000,
                            buttons: [
                                {
                                    text: 'Dismiss',
                                    role: 'cancel'
                                }
                            ]
                        })
                        .then(a => a.present())
                })
            }
        }
    }
}
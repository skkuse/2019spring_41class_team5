/**
 * Module      : Base64
 * Description : Convert image to base64 string
 * Dependencies: Vue
 * Author      : Junhyun Kim <junbread@skku.edu>
 */
export default {
    install(Vue, options) {
        Vue.prototype.$base64 = {
            encode: (image) => {
                return new Promise((callback, error) => {
                    const reader = new FileReader();
                    reader.onload = e => {
                        callback(e.target.result);
                    }

                    reader.readAsDataURL(image);
                });
            }
        }
    }
}
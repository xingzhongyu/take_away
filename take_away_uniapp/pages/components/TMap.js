export default function TMap(key) {
      return new Promise(function(resolve, reject) {
          window.init = function() {
              resolve(qq) //注意这里
          }
          var script = document.createElement("script");
          script.type = "text/javascript";
          script.src = "https://map.qq.com/api/js?v=2.exp&callback=init&key=" + 'G4MBZ-IGILU-4IDVJ-2BHBM-BWBHJ-3XFWT';
          script.onerror = reject;
          document.head.appendChild(script);
		  var script2 = document.createElement("script");
		  script.type = "text/javascript";
		  script.src = "https://3gimg.qq.com/lightmap/components/geolocation/geolocation.min.js";
		  script.onerror = reject;
		  document.head.appendChild(script2);
      })
  }
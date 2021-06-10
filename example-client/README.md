# Example Client


## Prerequisite
We assume that the cxt-platform is Running under `localhost:8080`.
See [../README.md](../README.md) for details. 

Then this micro-app should be up and reachable under [http://localhost:8200/](http://localhost:8200/)

## Develop
You can manually change the files within in this subfolder and the running micro app will show the changes. 


## Manually start this application
**+++This us no longer needed. The example client is started with all the other applications.+++**

You can serve this application with the static dev server of your choice.
For example

```
python -m SimpleHTTPServer 8200
#or
python3 -m http.server 8200
#or
npx serve -l 8200
```

The navigate to `http://localhost:8200/index.html`.


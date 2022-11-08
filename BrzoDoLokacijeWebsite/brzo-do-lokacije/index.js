const express = require('express');
const app = express();

app.use('/', express.static('../dist/brzodolokacije'))
app.set('view engine', 'pug');

app.get('/', function(_, response){
    response.sendFile('index.html', {root:__dirname});
});
app.listen(10035, "147.91.204.115");
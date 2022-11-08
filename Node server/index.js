const express = require('express');
const path = require('path')
const port = 10033;
const app = express();

app .use(express.static('./brzodolokacije')).get('*',function (req,res) {
    res.sendfile('/brzodolokacije/index.html');
    }).listen(10033);
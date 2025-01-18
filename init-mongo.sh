#!/bin/bash
mongosh mongodb://localhost:27017/dating?authSource=admin -u root -p toor <<EOF
use dating;

db.createCollection("user");
db.createCollection("reaction");
EOF

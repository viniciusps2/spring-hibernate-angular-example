#!/bin/bash
bash stop-all.sh
bash -c 'cd bank-admin && bash start.sh &'
bash -c 'cd bank-atm && bash start.sh &'
bash -c 'cd bank-ui && npm run dev'
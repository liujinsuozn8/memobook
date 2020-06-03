// import {add} from './add'
import {substract} from './substract'

console.log('this is config_introduction entry')

console.log(substract(6, 3))

import(/* webpackChunkName: 'add' */'./add').then(
    ({add})=>{ console.log( add(6, 3) ) }
)
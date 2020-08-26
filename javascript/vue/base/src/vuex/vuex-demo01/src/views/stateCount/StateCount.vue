<template>
    <div>
        <ul>
            <li>
                <router-link to="/stateCount/getter">stateCount--getter</router-link>
            </li>
            <li>
                <router-link to="/stateCount/mutation">stateCount--mutation</router-link>
            </li>
        </ul>
        <router-view/>
    </div>
</template>
const router = new require('koa-router')();

const province = [
    {
        id: '001',
        name: 'a'
    },
    {
        id: '002',
        name: 'b'
    },
    {
        id: '003',
        name: 'c'
    },
]

const cities = {
    '001': [
        {
            id: '110',
            name: 'a1'
        },
        {
            id: '120',
            name: 'a2'
        },
        {
            id: '130',
            name: 'a3'
        }
    ],
    '002': [
        {
            id: '210',
            name: 'b1'
        },
        {
            id: '220',
            name: 'b2'
        },
        {
            id: '230',
            name: 'b3'
        }
    ],
    '003': [
        {
            id: '310',
            name: 'c1'
        },
        {
            id: '320',
            name: 'c2'
        },
        {
            id: '330',
            name: 'c3'
        }
    ]
};

const areas = {
    '110': [
        {
            id: '111',
            name: 'a1-11',
        },
        {
            id: '112',
            name: 'a1-12',
        },
        {
            id: '113',
            name: 'a1-13',
        }
    ],
    '120': [
        {
            id: '121',
            name: 'a2-21',
        },
        {
            id: '122',
            name: 'a2-22',
        },
        {
            id: '123',
            name: 'a2-23',
        }
    ],
    '130': [
        {
            id: '131',
            name: 'a3-31',
        },
        {
            id: '132',
            name: 'a3-32',
        },
        {
            id: '133',
            name: 'a3-33',
        }
    ],
    '210': [
        {
            id: '211',
            name: 'b1-11',
        },
        {
            id: '212',
            name: 'b1-12',
        },
        {
            id: '213',
            name: 'b1-13',
        }
    ],
    '220': [
        {
            id: '221',
            name: 'b2-21',
        },
        {
            id: '222',
            name: 'b2-22',
        },
        {
            id: '223',
            name: 'b2-23',
        }
    ],
    '230': [
        {
            id: '231',
            name: 'b3-31',
        },
        {
            id: '232',
            name: 'b3-32',
        },
        {
            id: '233',
            name: 'b3-33',
        }
    ],
    '310': [
        {
            id: '311',
            name: 'c1-11',
        },
        {
            id: '312',
            name: 'c1-12',
        },
        {
            id: '313',
            name: 'c1-13',
        }
    ],
    '320': [
        {
            id: '321',
            name: 'c2-21',
        },
        {
            id: '322',
            name: 'c2-22',
        },
        {
            id: '323',
            name: 'c2-23',
        }
    ],
    '330': [
        {
            id: '331',
            name: 'c3-31',
        },
        {
            id: '332',
            name: 'c3-32',
        },
        {
            id: '333',
            name: 'c3-33',
        }
    ],
}

// 1. 返回省数据
router.get('/province', async ctx => {
    ctx.body = province;
});

// 2. 根据 省id 返回市数据
router.get('/city', async ctx => {
    const city = cities[ctx.query.id];
    if (city){
        ctx.body = city;
    } else {
        ctx.status = 400;
        ctx.body = 'error province id';
    }
});

// 3. 根据市id 返回区数据
router.get('/area', async ctx => {
    const area = areas[ctx.query.id];
    if (area){
        ctx.body = area;
    } else {
        ctx.status = 400;
        ctx.body = 'error city id';
    }
});

module.exports = router;
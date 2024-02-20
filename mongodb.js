db.customers.insertOne({
    _id: "ichwan",
    name: "Ichwan Sholihin"
})

db.products.insertMany([
    {
        _id: 5,
        name: "",
        price: 1000
    },
    {
        _id: 6,
        name: null,
        price: 11000
    }
])

db.products.insertMany([
    {
        _id: 7,
        name: "Jagung Bakar",
        price: new NumberLong("20000"),
        tags: ["makanan", "bakar"]
    },
    {
        _id: 8,
        name: "Es Buah",
        price: new NumberLong("1000"),
        tags: ["minuman", "seduh"]
    },
    {
        _id: 9,
        name: "Nasi Goreng",
        price: new NumberLong("15000"),
        tags: ["makanan", "goreng"]
    },
])

db.products.insertOne(
    {
        _id: 9,
        name: "Nasi Goreng",
        price: new NumberLong("15000"),
        tags: ["makanan", "goreng"]
    }
)

try {
    db.products.insertMany([
        {
            _id: 3,
            name: "Es teh",
            price: new NumberLong("4000")
        },
        {
            _id: 4,
            name: "Mi Aceh",
            price: new NumberLong("12000")
        }
    ], 
    { w: "majority", wtimeout: 100});    
} catch (error) {
    print(error);
}


db.orders.insertOne({
    _id: 1,
    items: [
        {
            _id: 1,
            name: "Bakso",
            price: new NumberLong("2000")
        },
        {
            _id: 2,
            name: "Mi Ayam",
            price: new NumberLong("10000")
        }   
    ]
})

//equals
db.products.find({price: {$eq: 2000}})
db.products.find({name: {$regex: /^Mi/}})

//greater than
db.products.find({price: {$gt: 2000}})

//in/until
db.products.find({price: { $in: [2000, 10000]}})
db.products.find({name: {$in: [/Mi/, /eh/]}})

//less than
db.products.find({price: {$lt: 2000}})

//not equals
db.products.find({price: {$ne: 2000}})

//not include
db.products.find({price: {$nin: [2000, 10000]}})

//and
db.products.find({ $and: [
    {price: {$in: [10000, 2000, 4000]}},
    {name: {$regex: /eh/}}
]})

//not
db.products.find({ price: { $not: { $gte: 10000}}})

//nor
db.products.find({ $nor: [
    {price: {$lt: 5000}},
    {name: {$regex: /eh/}}
]})

//or
db.products.find({ $or: [
    {price: {$lt: 5000}},
    {name: {$regex: /eh/}}
]})

//exists, mencari data yang tidak kosong
db.products.find({name: {$exists: true}})

//allow the use of aggregation expressions within query language
db.products.find({ $expr: { $lt: ["$price", 8000]}})

//json schema for validate name is not null
db.products.find({
    $jsonSchema: {
        required: ["name"],
        properties: {
            name: {
                type: "string",
                description: "must be a string and is required"
            },
            price: {
                type: "number"
            }
        }
    }
});

//modulo
db.products.find({price: {$mod: [5000, 0]}})

//where expressions
db.products.find({$where : function(){
    return this.price >= 5000
}})

//all, mencocokkan array yang berada pada elemen tertentu
db.products.find({tags: { $all: ["minuman, goreng"]}})

//mengambil document jika memenuhi kondisi tertentu
db.products.find({tags: { $elemMatch: { $in: ["makanan"]}}})

//mengambil jumlah value
db.products.find({tags: { $size: 2}})
db.products.find({}).size()

//mengambil sebagian field
db.products.find({}, {name: 1, price: 1})

//limit dan offset
db.products.find({}).limit(4).skip(3)

//sort(-1 descending, 1 ascending)
db.products.find({}).sort({name: -1})

//mengubah satu dokumen, untuk updateMany menggunakan $and
db.products.updateOne({_id: 1}, { $set: {price: 12000}})

//mengubah nama field
db.products.updateMany({}, {$rename: {price: "harga"}})

//add last modified date
db.products.updateMany({}, { $currentDate: { lastModDate: { $type: "date"}}})

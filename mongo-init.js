rsconf = {
    _id: "rs0",
    members: [{ _id: 0, host: "mongodb:27017", priority: 1.0 }],
};
rs.initiate(rsconf);
rs.status();

// db.createUser({
//     user: 'developer',
//     pwd: 'Develop3r',
//     roles: [{
//         role: 'readWrite',
//         db: 'ginormitron'
//     }]
// })

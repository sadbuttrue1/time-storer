db.createUser(
    {
        user: "time",
        pwd: "saver",
        roles: [
            {
                role: "readWrite",
                db: "time"
            }
        ]
    }
);
const admin = require('firebase-admin');
const serviceAccount = JSON.parse(process.env.FIREBASE_SERVICE_ACCOUNT);

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

const db = admin.database();

// Performing a simple read operation on the root of the database.
db.ref().once('value')
  .then(snapshot => {
    if (snapshot.exists()) {
      console.log('Read test passed: Database is accessible.');
    } else {
      console.error('Read test failed: Unable to access the database.');
      process.exit(1);
    }
  })
  .catch(error => {
    console.error('Read test failed:', error);
    process.exit(1);
  });

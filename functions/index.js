const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.addDefaultTeam = functions.firestore
    .document('user/{userId}')
    .onCreate((snap, context) => {
      if (snap) {
        return snap.ref.update({
                    team: snap.data()['email']
                })
      }

      return "snap was null or empty";
    });

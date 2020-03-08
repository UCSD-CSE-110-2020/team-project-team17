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

exports.sendTeamNotifications = functions.firestore
   .document('user/{userId}')
   .onUpdate((snap, context) => {
     // Get an object with the current document value.
     // If the document does not exist, it has been deleted.
     const document = snap.exists ? snap.data() : null;

     if (document) {

       var t = document.team.replace('@', '')

       var message = {
         notification: {
           title: 'New Team Member',
           body: document.name + ' has joined the team.'
         },
         topic: t
       };

       return admin.messaging().send(message)
         .then((response) => {
           // Response is a message ID string.
           console.log('Successfully sent message:', response);
           return response;
         })
         .catch((error) => {
           console.log('Error sending message:', error);
           return error;
         });
     }

     return "document was null or empty";
   });

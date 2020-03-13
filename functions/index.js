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

exports.sendTeamInvite = functions.firestore
          .document('invitation/{invitationId}')
          .onCreate((snap, context) => {

          const document = snap.exists ? snap.data() : null;

          if (document) {
             var t = document.to.replace('@', '.')

             var message = {
               notification: {
                 title: 'New Invitation',
                 body: document.sender + ' has sent you an invite.'
               },
               topic: t
             };

             console.log("Sending message " + message)
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

           console.log("No document found")
           return "document was null or empty";
         });

exports.sendTeamNotifications = functions.firestore
   .document('user/{userId}')
   .onUpdate((snap, context) => {
     // Get an object with the current document value.
     // If the document does not exist, it has been deleted.
     const document = snap.after.exists ? snap.after.data() : null;

     console.log("Checking if document exists")


     if (document) {

       var t = document.team.replace('@', '')

       var message = {
         notification: {
           title: 'New Team Member',
           body: document.name + ' has joined the team.'
         },
         topic: t
       };

       console.log("Sending message " + message)
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

     console.log("No document found")
     return "document was null or empty";
   });

exports.sendProposalChange = functions.firestore
   .document('proposal/{proposalId}')
   .onUpdate((snap, context) => {
     // Get an object with the current document value.
     // If the document does not exist, it has been deleted.
     const before = snap.before.exists ? snap.before.data() : null;
     const after = snap.after.exists ? snap.after.data() : null;

     console.log("Checking if document exists")


     if (after) {

       var t = after.teamId.replace('@', '')

       var message;

       if (!before.scheduled && after.scheduled) {
         message = {
           notification: {
              title: 'Group Walk has been Scheduled',
              body: ':)'
            },
            topic: t
          };
       } else {
         var beforeR = JSON.parse(before.route)
         var afterR = JSON.parse(after.route)

         console.log(beforeR.responses)
         console.log(afterR.responses)

         for(var key in afterR.responses){
           if(afterR.responses[key] !== beforeR.responses[key]){
             console.log(key)
             message = {
                        notification: {
                          title: 'Group Walk: Status Update',
                          body: key + ' has changed their status to ' + afterR.responses[key]
                        },
                        topic: t
                      };
             break;
           }
         }
       }

       console.log("Sending message " + message)
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

     console.log("No document found")
     return "document was null or empty";
   });

exports.sendProposalDelete = functions.firestore
  .document('proposal/{proposalId}')
  .onDelete((snap, context) => {
    // Get an object with the current document value.
    // If the document does not exist, it has been deleted.
    const document = snap.exists ? snap.data() : null;

    console.log("Checking if document exists")


    if (document) {

      var t = document.teamId.replace('@', '')

      var message = {
        notification: {
          title: 'Group Walk Withdrawn',
          body: ':('
        },
        topic: t
      };

      console.log("Sending message " + message)
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

    console.log("No document found")
    return "document was null or empty";
  });
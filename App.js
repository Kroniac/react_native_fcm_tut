import React, { Component } from 'react';
import { View, Text } from 'react-native';

const FcmUtils = require('./libs/fcm_utils')


export default class App extends Component {

  async componentDidMount() {
    this._registerFcm();
    this._foregroundNotificationsListener();
  }

  _registerFcm = () => new Promise((resolve) => {
    FcmUtils.CheckPermissionAndReturnFcmToken()
      .then((fcmToken) => {
        // your fcm token
        // send it your server or store it. Use it as per your need
      })
      .catch((err) => {
        // error handling logic
      });
  })

  _foregroundNotificationsListener = async () => {
    // to process message broadcasted from onMessageReceived method
    this.messageListener = firebase.messaging().onMessage((message) => {
      // put your logic to process message
    })

    // get notification data when notification is clicked when app is in foreground
    this.notificationOpenedListener = firebase.notifications().onNotificationOpened((notificationData) => {
      
    });

    // get notification data when notification is clicked to open app when app is in background
    firebase.notifications().getInitialNotification()
      .then((notificationData) => { 
        // put your logic here
    })
  }

  render() {
    return (
      <View style = {{ flex: 1, justifyContent: 'center', alignItems: 'center' }} >
        <Text>FCM Tutorial</Text>
      </View>
    )
  }
}


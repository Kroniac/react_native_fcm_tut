export const ReturnFcmToken = () => new Promise(async (resolve) => {
  try {
    const fcmToken = await firebase.messaging().getToken();
    // you can put here some logic like storing fcmToken in sharedpreferences
    resolve(fcmToken);
  } catch (err) {
    reject(err);
  }
});

export const RequestPermission = () => new Promise(async (resolve, reject) => {
  try {
    await firebase.messaging().requestPermission();
    resolve();
  } catch (error) {
    // User has rejected permissions
    reject(error);
  }
})

export const CheckPermissionAndReturnFcmToken = () => new Promise(async (resolve, reject) => {
  const enabled = await firebase.messaging().hasPermission();
  if (enabled) {
    const fcmToken = await this._returnFcmToken();
    resolve(fcmToken);
  } else {
    try {
      await this._requestPermission();
      const fcmToken = await this._returnFcmToken();
      resolve(fcmToken);
    } catch (e) {
      reject(e);
    }
  }
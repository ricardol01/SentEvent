/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  NativeModules,
  DeviceEventEmitter,
} from 'react-native';
import RCTDeviceEventEmitter from 'RCTDeviceEventEmitter';
export default class testsocket extends Component {
  constructor()
  {
    super();
    this.state={
      result:''
    }
  }
  componentWillMount()
  {
    DeviceEventEmitter.addListener('testEvent',(msg)=>{
      this.setState({result:msg});
    });
    DeviceEventEmitter.addListener('EventToAndroid',(msg)=>{console.log(msg);
  });
  }
  async promise()
  {
    try{
      var {
        msg
      }=await NativeModules.ToastModuleTest.testPromise();
      this.setState({result:msg});
    } catch (error){
      console.log(error);
    }
  }
  render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity onPress={()=>{
            NativeModules.ToastModuleTest.testCallback((msg)=>{
              this.setState({result: msg});
            });
          }}>
          <Text style={styles.welcome}>
            Callback
          </Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={()=>{
            this.promise();
            console.log(this.state.result);
          }}>
          <Text style={styles.welcome}>
            Promise
          </Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={()=>{
            NativeModules.ToastModuleTest.testEvent();
          }}>
          <Text style={styles.welcome}>
            Event
          </Text>
        </TouchableOpacity>
        <Text style={styles.instructions}>
          {this.state.result}
        </Text>
        <TouchableOpacity onPress={()=>{
          NativeModules.ToastModuleTest.Eventdispatcher("Type1","This is an event sent by RN");
        }}>
          <Text style={styles.welcome}>
            Event1ToAndroid
          </Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={()=>{
          NativeModules.ToastModuleTest.Eventdispatcher("Type2","This is an event sent by RN");
        }}>
          <Text style={styles.welcome}>
            Event2ToAndroid
          </Text>
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('testsocket', () => testsocket);
hahahah

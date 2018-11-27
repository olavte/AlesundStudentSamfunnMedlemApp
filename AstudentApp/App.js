/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View, WebView} from 'react-native';
import AndroidWebView from 'react-native-webview-file-upload-android';

type Props = {};
export default class App extends Component<Props> {
    render() {
        return (
            <View style={{flex:1}}>
                {Platform.select({
                    android:  () => <AndroidWebView
                        source={{ uri: 'https://staging.astudent.no' }}
                        scalesPageToFit={false}
                    />,
                    ios:      () => <WebView
                        source={{ uri: 'https://staging.astudent.no' }}
                        scalesPageToFit={false}
                    />
                })()}
            </View>
        );
    }
}

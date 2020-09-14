import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Native Comunication',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage(title: 'Native Comunication'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = const MethodChannel("MyChanel");
  String message = "No message from Native";
  // this method calls the native function
  Future<void> callNative() async {
    String messageFromNative = "No message from Native";

    try {
      messageFromNative = await platform.invokeMethod("myNativeFunction");
      print(messageFromNative);
    } on PlatformException catch (e) {
      print("error: " + e.details);
      messageFromNative = e.details;
    }

    setState(() {
      message = messageFromNative;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(message),
            RaisedButton(
                child: Text('Call Native Function'), onPressed: callNative)
          ],
        ),
      ),
    );
  }
}

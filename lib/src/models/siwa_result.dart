import 'package:meta/meta.dart';

class SIWAResult {
  SIWAResult({
    @required this.accessToken,
    @required this.idToken,
  })  : assert(accessToken != null),
        assert(idToken != null);

  factory SIWAResult.fromJson(Map<String, String> json) {
    return SIWAResult(
      accessToken: json['accessToken'],
      idToken: json['idToken'],
    );
  }

  final String accessToken;
  final String idToken;
}

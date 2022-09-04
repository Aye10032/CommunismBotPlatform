from flask import Flask, jsonify, make_response, request
from huoZiYinShua import *

app = Flask(__name__)
HZYS = huoZiYinShua("./settings.json")


@app.route('/yinshua', methods=['POST'])
def create_task():
    text = request.args.get('text')
    HZYS.export(text, "./Output.wav", inYsddMode=True)
    return make_response(jsonify({'success': 'message send', 'code': 201}), 201)


@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)


if __name__ == '__main__':
    app.run(host='0.0.0.0')

(function () {

    angular
        .module("pcApp", [])
        .controller("calcController", function ($http) {
            var ctrl = this;

            ctrl.loading = false;

            ctrl.calc = function () {
                if (ctrl.loading) {
                    return;
                }
                ctrl.loading = true;
                var data = {set: {}};
                angular.forEach(ctrl.input.set, function (val, idx) {
                    data.set[idx + 1] = val;
                });
                ctrl.result = {};
                $http.post("./calc", data).then(function (data) {
                    ctrl.result = data.data;
                    ctrl.loading = false;
                });
            };

            ctrl.add = function () {
                ctrl.input.set.push({
                    top: "?",
                    bottom: "?"
                })
            };

            ctrl.remove = function (idx) {
                ctrl.input.set.splice(idx, 1);
            };

            ctrl.result = {};

            ctrl.input = {
                "set": [
                    {"top": "001", "bottom": "0"},
                    {"top": "01", "bottom": "011"},
                    {"top": "01", "bottom": "101"},
                    {"top": "10", "bottom": "001"}
                ]
            };
        });

})();
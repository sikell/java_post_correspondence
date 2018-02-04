(function () {

    angular
        .module("pcApp", [])
        .controller("calcController", function ($http) {
            var ctrl = this;

            ctrl.loading = false;
            ctrl.failure = false;

            ctrl.calc = function () {
                if (ctrl.loading) {
                    return;
                }
                ctrl.loading = true;
                var data = {blocks: {set: {}}};
                angular.forEach(ctrl.input.set, function (val, idx) {
                    data.blocks.set[idx + 1] = val;
                });
                data.solutions = ctrl.input.solutions;
                ctrl.result = {};
                $http.post("./calc", data).then(function (data) {
                    ctrl.failure = false;
                    ctrl.result = data.data;
                    ctrl.loading = false;
                }, function() {
                    ctrl.failure = true;
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

            ctrl.getNumber = function(num) {
                return new Array(num);
            };

            /*ctrl.input = {
                "solutions": "1",
                "set": [
                    {"top": "001", "bottom": "0"},
                    {"top": "01", "bottom": "011"},
                    {"top": "01", "bottom": "101"},
                    {"top": "10", "bottom": "001"}
                ]
            };*/
            ctrl.input = {
                "solutions": "1",
                "set": [
                    {"top":"10","bottom":"1"},
                    {"top":"11","bottom":"01"},
                    {"top":"01","bottom":"0"},
                    {"top":"0","bottom":"010"}
                ]
            };
        });

    angular
        .module("pcApp")
        .filter('orderByLength', function () {
            return function (array, reverse) {
                var filtered = [];
                angular.forEach(array, function (item) {
                    filtered.push(item);
                });
                filtered.sort(function (a, b) {
                    return (a.result.length > b.result.length ? 1 : -1);
                });
                if (reverse) filtered.reverse();
                return filtered;
            };
        });

})();
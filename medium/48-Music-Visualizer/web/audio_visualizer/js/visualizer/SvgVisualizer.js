class SvgVisualizer {

    audioHandler = new AudioHandler();
    svg;
    data;

    /**
     * @param props: height + width + parentNode
     */
    constructor(props) {
        this.data = new Uint8Array(props.dataLength);
        this.svg = this._createSvg(props);
        this.addRectsToSvg(props);
    }

    addRectsToSvg(props) {
        let _odd = false;
        let isOdd = () => {
            return _odd = !_odd;
        }
        let angle = (i) => {
            let angle = i * 180 / this.data.length;
            if (_odd) angle += 91;
            else angle -= 91;
            return angle + props.circleAngle;
        }
        let dx = (angle) => {
            return Math.cos(angle * Math.PI / 180) * props.circle.radius;
        }
        let dy = (angle) => {
            return Math.sin(angle * Math.PI / 180) * props.circle.radius;
        }

        this.svg.selectAll("rect")
                .data(this.data)
                .enter()
                .append("rect")
                .attr("x", (d, i) => {
                    if (isOdd()) i = this.data.length - i;
                    return props.r + dx(angle(i));
                })
                .attr("y", (d, i) => {
                    if (isOdd()) i = this.data.length - i;
                    return props.r + dy(angle(i))
                })
                .attr("width", props.r * props.barWMultiplier / this.data.length)
                .attr("rx", props.r * props.barWMultiplier * 0.5 / this.data.length)
                .attr("transform", (d, i) => {
                    if (isOdd()) i = this.data.length - i;
                    let _angle = angle(i);
                    let x = props.r + dx(_angle);
                    let y = props.r + dy(_angle);
                    _angle -= 90 + props.barAngle;
                    return `rotate(${_angle}, ${x}, ${y})`;
                });
    }

    /**
     * @param handler - audio handler with web audio api
     */
    setAudioHandler(handler) {
        this.audioHandler = handler;
    }

    renderLoop() {
        requestAnimationFrame(this.renderLoop.bind(this));

        this.audioHandler.getData(this.data);

        this.svg.selectAll("rect")
                .data(this.data)
                .attr("height", (d) => {
                    let height = d;
                    if (height < 0) height = 0;
                    return height;
                })
                .attr("fill", (d, i) => {
                    let fill = d * 255 / this.data.length;
                    let r = fill;
                    let g = 255 - fill;
                    let b = 255;
                    return `rgb(${r},${g},${b})`;
                })
    }

    // private:

    _createSvg(props) {
        let svg = d3.select(props.parentNode)
                    .append("svg")
                    .attr("height", props.r * 2)
                    .attr("width", props.r * 2);

        let circle = props.circle;

        svg.append("circle")
            .attr("fill", "none")
            .attr("cx", props.r)
            .attr("cy", props.r)
            .attr("r", circle.radius)
            .attr("stroke", "#157483")
            .attr("stroke-width", circle.width);

        return svg
    }
}
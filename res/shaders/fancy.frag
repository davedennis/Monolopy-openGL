uniform vec2 resolution;
uniform float time;
uniform sampler2D backbuffer;

void main( void ) {    
    vec2 pa = (gl_FragCoord.xy * 2.0 - resolution) / resolution.y;
    
    vec3 col = vec3(0.0);
    col.r = sin(pa.x * 30.);
    col.g = sin(pa.x * 30.) * 0.5 + 0.5;
    col.b = sin(pa.x * 10.) * 0.8 + 0.2;
	
	vec2 p = ( gl_FragCoord.xy / resolution.xy);
//	float time = time * .1 + ((.25+.05*sin(time*.1))/(length(p.xy)+.07))* 2.2;
	
	vec2 uv = vec2(p.x*sin(time), p.y*sin(time));
	
	vec3 coll = texture2D(backbuffer, uv).xyz;
    	coll += col;
    gl_FragColor = vec4(coll, 1.0);
}
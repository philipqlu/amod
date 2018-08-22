function array = jmexArray(name, tensor)

array = ch.ethz.idsc.jmex.DoubleArray( ...
  name, ...
  int32(size(tensor)'), ...
  double(tensor(:)) ...
);

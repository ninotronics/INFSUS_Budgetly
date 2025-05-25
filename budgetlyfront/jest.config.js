module.exports = {
  testEnvironment: 'jsdom', // <-- change this line!
  transform: {
    '^.+\\.(ts|tsx)$': 'ts-jest', // also add tsx for React components
  },
  testRegex: '(/__tests__/.*|(\\.|/))(test|spec)\\.(ts|tsx|js|jsx)$', // add tsx/jsx
  moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'node'],   // add tsx/jsx
};